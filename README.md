# spring-persistence
This project is to test some behaviour of jpa, the test is based on spring boot 2.3.0.RELEASE

## Background
- EntityManager is the interface to interact with the persistence context
- Persistence context is first-level cache where all the entities are fetched from the database or saved to the database.
- Persistence context also keeps track of any changes made into a managed entity.
- If anything changes during a transaction, then the entity is marked as dirty.
- When the transaction completes, these changes are flushed into persistent storage.

Persistence contexts are available in two types:
* Transaction-scoped persistence context
* Extended-scoped persistence context

## Test if entity returned by JpaRepository the same object

| Find entity twice from JpaRepository | Same Object? |
| ------------------------  | ---------------------|
| In same method | Y |
| One is found in a method in another service | Y |
| One is found in a transactional method in another service | Y |
| One is found in an asynchronous method in another service | **N** |

## Test Auto Flush
### 1. Update a user without save, then save a new user
- The change to the user entity is flushed back to DB
```  
public void isFlush(String email) {
    User user =  findUserByEmail(email);
    user.setMobile(new Date().toLocaleString());

    User newUser = newUser();
    userRepository.save(newUser);
}
``` 
### 2. Save a new user, then find and update a user without save
- The change to the user entity is not flushed.
```
public void isFlush2(String email) {
    User newUser = newUser();
    userRepository.save(newUser);

    User user =  findUserByEmail(email);
    user.setMobile(new Date().toLocaleString());
}
```

### 3. Same as above, but annotated with @Transactional
- Invoke this method **inside** this class, the change to user entity is **not** flushed
- Invoke this method from **outside** the class, the change to user entity is flushed
```
@Transactional
public void isFlush3(String email) {
    User newUser = newUser();
    userRepository.save(newUser);

    User user =  findUserByEmail(email);
    user.setMobile(new Date().toLocaleString());
}
```

## Note
- The PersistenceContext in Spring is default to **PersistenceContextType.TRANSACTION** 
- Although the objects returned has the same reference, it still touch the database **twice**
- JpaRepository annotated all CRUD methods as @Transactional
