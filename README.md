# PAO

## Requirements

### Stage I

1. System definition
Create a list, based on the chosen theme, containing at least 10 actions or queries that can be performed in the system, and a list with at least 8 types of objects.

2. Implementation
Implement a Java application based on the definitions from the first point.

The application must include:

- simple classes with private or protected attributes and accessor methods
- at least 2 different collections capable of managing the previously defined objects (for example `List`, `Set`, `Map`), with at least one sorted collection; one-dimensional or two-dimensional arrays may be used only if collections are not covered by the checkpoint date
- inheritance used to create additional classes and to use them inside collections
- at least one service class that exposes the system operations
- a `Main` class that calls the service layer

### Stage II

1. Extend the first stage project by adding persistence with a relational database and JDBC.
Create services that expose create, read, update, and delete operations for at least 4 of the defined classes. Generic singleton services must be implemented for reading from and writing to the database.

2. Create an audit service.
Implement a service that writes to a CSV file every time one of the actions described in Stage I is executed. The file structure must be: `action_name, timestamp`.
