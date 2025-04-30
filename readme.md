# Hyperskill Simple Banking System in Java

[A little project from Hyperskill](https://hyperskill.org/projects/93).

### Subject area
Involves building a little stateful console-based client app for a bank user. Primary entity is not an account, but a card.

User can:

1. Create a card-account;
2. Login into the account;
3. Add income; 
4. Transfer money to another user;
5. Exit the account;
6. Close the account.

Account data is persisted over app runs. 

### Technologies
Uses JDBC + SQLite for persistence (DB in a local file). 

Used this little project to remember/refine my understanding of the clean architecture pattern (Domain-Application-Presentation).