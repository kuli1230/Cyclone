## Cyclone
Cyclone is an MySQL and SQLLite util library with integrated [HikariCP](http://brettwooldridge.github.io/HikariCP/) connection pool.
The main feature is the Query Builder System with the Builder design pattern.
You don't have to write the complete SQL-Query string down anymore. You can instead use a cool and easy builder API.
Another feature is that you can create classes which are the same as a table and then load results of a "SELECT" into a list of your class and use it, without any parsing of the result set or something like that.
You can now also get the row SQL query from all builders with the sql() method after you have called build() with a query.

## Cyclone now has full SQLLite support!
The only thing that is different to MySQL is that you have to use the 'database' method in the settings builder as a path to a '.db' file and set the type to 'Type.SQL_LITE'.
The directories to the '.db' file are created from Cyclone automatically.

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
// Default Cyclone database type is MySQL
Cyclone cyclone = new Cyclone(new CycloneSettings.Builder()
                .host("localhost")
                .port(3306)
                .user("root")
                .password("")
                .database("cyclone")
                .poolSize(15)
                .build());
                
// Set the cyclone instance to database type SQLLite
// The 'database' field is now the path to the .db file
// Directories to the file are created by Cyclone
Cyclone cyclone = new Cyclone(new CycloneSettings.Builder()
				.database("data/test.db")
				.type(Type.SQL_LITE)
				.build());
```
**Connecting**
```java
cyclone.connect();
```
**Create**
```java
try {
    cyclone.create(new CreateQuery.Builder()
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
    cyclone.insert(new InsertQuery.Builder()
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
    DBResult dbResult = cyclone.select(new SelectQuery.Builder()
            .select("*")
            .from("test")
            .build());

	// Example 1
    ResultSet set = dbResult.resultSet();
    while (set.next()) {
        System.out.println("ID: " + set.getInt("id") +
                " Name: " + set.getString("name") +
                " UUID: " + set.getString("uuid"));
    }
    // Example 2
    // 'id' is an Integer in the DB, so the return type is automatically an Integer
    // Same for other types
    for (DBRow row : dbResult.rows()) {
    	System.out.println("ID: " + row.get("id") +
        		" Name: " + row.get("name") + 
                " UUID: " + row.get("uuid"));
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
    List<TestTable> result = cyclone.select(new SelectQuery.Builder()
            .select("*")
            .from("test")
            .limit(2)
            .build(), TestTable.class);
    result.forEach(System.out::println);
} catch (SQLException e) {
    e.printStackTrace();
}
```
**Update**
```java
try {
    cyclone.update(new UpdateQuery.Builder()
            .update("test")
            .set("name", "Jacky")
            .set("uuid", "0000")
            .where("id", "1")
            .build());
} catch (SQLException e) {
    e.printStackTrace();
}
```
**Delete**
```java
try {
	cyclone.delete(new DeleteQuery.Builder().from("test").where("id", "1").build());
} catch (SQLException e) {
	e.printStackTrace();
}
```
**Drop**
```java
try {
    cyclone.drop(new DropQuery.Builder()
            .drop("test")
            .build());
} catch (SQLException e) {
    e.printStackTrace();
}
```
## License
Licensed under the GNU General Public License, Version 3.0.
