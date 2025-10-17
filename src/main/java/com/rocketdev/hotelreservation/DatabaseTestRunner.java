package com.rocketdev.hotelreservation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class DatabaseTestRunner implements CommandLineRunner {

    private final DataSource dataSource;

    public DatabaseTestRunner(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🔍 Veritabanı bağlantısı test ediliyor...");

        try (Connection connection = dataSource.getConnection()) {
            System.out.println("✅ Bağlantı başarılı! Veritabanı adı: " + connection.getCatalog());
        } catch (Exception e) {
            System.err.println("❌ Bağlantı başarısız: " + e.getMessage());
        }
    }
}
