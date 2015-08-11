## Cyclone
Cyclone is an MySQL util library with integrated HikariCP connection pool.
The main feature is the Query Builder System with the Builder design pattern.
You don't have to write the complete SQL-Query down anymore. You can instead use e.g. the CreateQuery or directly the CreateQueryBuilder to build your query to create a SQL CREATE query. 

## Examples

**Initializing**
```java
Cyclone cyclone = new Cyclone(new DBConnectionSettings.SettingsBuilder()
                .host("localhost")
                .port(3306)
                .user("root")
                .password("")
                .database("cyclone")
                .build());
```
**Connecting**
```java
cyclone.connect();
```
**Create**
```java
        try {
            cyclone.create(new CreateQuery.CreateQueryBuilder()
                    .create("test")
                    .ifNotExists(true)
                    .primaryKey("id")
                    .values("id int auto_increment", "name varchar(255)", "uuid varchar(255)")
                    .build());
        } catch (SQLException e) {
            e.printStackTrace();
        }
```
**Insert**
```java
        try {
            cyclone.insert(new InsertQuery.InsertQueryBuilder()
                    .into("test")
                    .values("0", "JackWhite20", "000000-000000-000000")
                    .build());
        } catch (SQLException e) {
            e.printStackTrace();
        }
```
**Select**
```java
        try {
            DBResult dbResult = cyclone.select(new SelectQuery.SelectQueryBuilder()
                    .select("*")
                    .from("test")
                    .build());
            
            ResultSet set = dbResult.resultSet();
            while (set.next()) {
                System.out.println("ID: " + set.getInt("id") + 
                        " Name: " + set.getString("name") + 
                        " UUID: " + set.getString("uuid"));
            }
            dbResult.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
```
**Update**
```java
        try {
            cyclone.update(new UpdateQuery.UpdateQueryBuilder()
                    .update("test")
                    .set("name", "Jacky")
                    .where("id", "1")
                    .build());
        } catch (SQLException e) {
            e.printStackTrace();
        }
```
**Drop**
```java
        try {
            cyclone.drop(new DropQuery.DropQueryBuilder()
                    .drop("test")
                    .build());
        } catch (SQLException e) {
            e.printStackTrace();
        }
```