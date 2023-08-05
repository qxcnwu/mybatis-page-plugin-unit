package com.qxc.mybatis.interceptor;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;

/**
 * @Author qxc
 * @Date 2023 2023/8/5 14:16
 * @Version 1.0
 * @PACKAGE com.qxc.mybatis.interceptor
 */
@Intercepts(
        @Signature(
                type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class, Integer.class}
        )
)
public class PageInterceptor implements Interceptor {
    private final HashMap<String, PageHelper> map = new HashMap<>();
    private final HashSet<String> set = new HashSet<>();
    private Boolean catchError = false;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Connection connection = (Connection) invocation.getArgs()[0];
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        final BoundSql boundSql = statementHandler.getBoundSql();
        // 获取方法对象
        HashMap<String, Object> paramObj = (HashMap<String, Object>) boundSql.getParameterObject();
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        final String statementId = mappedStatement.getId();
        // 判断当前的语句
        if (set.contains(statementId)) {
            return invocation.proceed();
        } else if (!map.containsKey(statementId)) {
            // 对语句进行验证查询是否需要进行分页
            final PageHelper pageHelper = getPageHelper(statementId, paramObj);
            // 不存在则加入set中
            if (pageHelper != null) {
                map.put(statementId, pageHelper);
            } else {
                // 不需要分页直接执行
                set.add(statementId);
                return invocation.proceed();
            }
        }
        // 当前都是需要分页的插件，开始查询,首先得到查询的page与count
        final PageHelper pageHelper = map.get(statementId);
        int page = "".equals(pageHelper.getPageName()) ? pageHelper.getPage() : (int) paramObj.get(pageHelper.getPageName());
        int count = "".equals(pageHelper.getCountName()) ? pageHelper.getCount() : (int) paramObj.get(pageHelper.getCountName());
        count = Math.max(count, 1);
        // 查询所有满足条件的对象的集合
        int all = getAll(invocation, selectAll(boundSql.getSql()), metaObject);
        if (catchError && page >= all / count) {
            throw new RuntimeException("query page bigger than all number! max page is " + all / count + " but get " + page);
        }
        // 执行全部查询
        int start = (page - 1) * count;
        String sqlLimit = generaterPageSql(boundSql.getSql(), start, count);
        metaObject.setValue("delegate.boundSql.sql", sqlLimit);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        catchError = Boolean.valueOf(properties.getProperty("catchError"));
        Interceptor.super.setProperties(properties);
    }

    private int getAll(Invocation invocation, String countSql, MetaObject metaObject) throws SQLException {
        Connection connection = (Connection) invocation.getArgs()[0];
        PreparedStatement countStatement = connection.prepareStatement(countSql);
        ParameterHandler parameterHandler = (ParameterHandler) metaObject.getValue("delegate.parameterHandler");
        parameterHandler.setParameters(countStatement);
        ResultSet rs = countStatement.executeQuery();
        int all = 0;
        if (rs.next()) {
            all = rs.getInt(1);
        }
        rs.close();
        countStatement.close();
        return all;
    }

    private String selectAll(String sql) {
        return "select count(*) from ( " + sql + " ) temp";
    }

    private PageHelper getPageHelper(String id, HashMap<String, Object> paramObj) {
        final int idx = id.lastIndexOf('.');
        String className = id.substring(0, idx);
        String methodName = id.substring(idx + 1);
        try {
            final Class<?> aClass = Class.forName(className);
            Method method = null;
            for (Method md : aClass.getMethods()) {
                if (methodName.equals(md.getName())) {
                    method = md;
                    break;
                }
            }
            final PageSplit methodAnnotation = method.getAnnotation(PageSplit.class);
            final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            int page = 0;
            int count = 0;
            String pageName = "";
            String countName = "";
            // 首先检查方法上的默认值
            if (methodAnnotation != null) {
                page = methodAnnotation.page();
                count = methodAnnotation.count();
            }
            // 检查所有方法上的annotation，替换默认值
            for (Annotation[] parameter : parameterAnnotations) {
                String name = "";
                boolean pno = false;
                boolean cno = false;
                for (Annotation ano : parameter) {
                    if (ano instanceof PageValue) {
                        PageValue pv = (PageValue) ano;
                        if (pv.type().equals(PageType.PAGE)) {
                            page = pv.value();
                            pno = true;
                        } else {
                            count = pv.value();
                            cno = true;
                        }
                    } else if (ano instanceof Param) {
                        name = ((Param) ano).value();
                    }
                }
                if (pno) {
                    pageName = name;
                }
                if (cno) {
                    countName = name;
                }
            }
            // 返回返回条件的查询对象
            if (("".equals(pageName) && "".equals(countName)) || page == 0 && count == 0) {
                return null;
            }
            return new PageHelper(page, count, pageName, countName);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String generaterPageSql(String sql, int start, int count) {
        return sql + " limit " + start + " , " + count;
    }

    static class PageHelper {
        int page;
        int count;
        String pageName;
        String countName;

        public PageHelper() {
        }

        public PageHelper(int page, int count, String pageName, String countName) {
            this.page = page;
            this.count = count;
            this.pageName = pageName;
            this.countName = countName;
        }

        public int getPage() {
            return page;
        }

        public PageHelper setPage(int page) {
            this.page = page;
            return this;
        }

        public int getCount() {
            return count;
        }

        public PageHelper setCount(int count) {
            this.count = count;
            return this;
        }

        public String getPageName() {
            return pageName;
        }

        public PageHelper setPageName(String pageName) {
            this.pageName = pageName;
            return this;
        }

        public String getCountName() {
            return countName;
        }

        public PageHelper setCountName(String countName) {
            this.countName = countName;
            return this;
        }

        @Override
        public String toString() {
            return "PageHelper{" +
                    "page=" + page +
                    ", count=" + count +
                    ", pageName='" + pageName + '\'' +
                    ", countName='" + countName + '\'' +
                    '}';
        }
    }
}


