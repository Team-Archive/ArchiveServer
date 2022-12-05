package site.archive.domain;

import org.springframework.test.context.jdbc.Sql;

@JpaTest
@Sql({"classpath:sql/default/user.sql",
      "classpath:sql/default/archive.sql"})
public abstract class JpaTestSupport extends MySQLTestContainer {
}
