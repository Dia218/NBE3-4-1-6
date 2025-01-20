package org.team6.coffeebeanery.common;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DataSourceTest {
    
    @Autowired
    private DataSource dataSource;  // DataSource 자동 주입
    
    @Test
    public void testDataSourceConnection() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            assertNotNull(connection);
            System.out.println("DB 연결 성공!");
        }
    }
}