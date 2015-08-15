## Cyclone
Cyclone is an MySQL util library with integrated [HikariCP](http://brettwooldridge.github.io/HikariCP/) connection pool.
The main feature is the Query Builder System with the Builder design pattern.
You don't have to write the complete SQL-Query down anymore. You can instead use cool and easy builder.
Another feature is that you can create classes which are the same as a table and then load results of a "SELECT" into a list of your class and use it, without any parsing of the result set or something like that.

## Installation
- Install [Maven 3](http://maven.apache.org/download.cgi)
- Checkout/Clone/Download this repo
- Install it with: ```mvn clean install```

**Maven dependency**
```xml
<dependency>
    <groupId>de.jackwhite20</groupId>
    <artifactId>cyclone</artifactId>
    <version>0.1-SNAPSHOT</version>
</dependency>
```

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
            .ifNotExists(false)
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
            .values("0", "JackWhite20", "067e6162-3b6f-4ae2-a171-2470b63dff00")
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
**Custom Select**

In the TestTable class are 3 fields (id, name and uuid) like the table 'test'.
```java
try {
    List<TestTable> result = cyclone.select(new SelectQuery.SelectQueryBuilder()
            .select("*")
            .from("test")
            .build(), TestTable.class);
    result.forEach(System.out::println);
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
            .set("uuid", "0000")
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
## License
Licensed under the GNU General Public License, Version 3.0.
