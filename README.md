# Cyclone
Cyclone is an MySQL util library with integrated HikariCP connection pool and more.

## Examples
```java
Cyclone cyclone = new Cyclone(new DBConnectionSettings.SettingsBuilder()
                .host("localhost")
                .port(3306)
                .user("root")
                .password("")
                .database("cyclone")
                .build());
```
```java
cyclone.connect();
```