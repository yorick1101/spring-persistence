# spring-persistence
This project is to test some behaviour of jpa 

## Test if entity returned by JpaRepository the same object

| Find entity twice from JpaRepository | Same Object? |
| ------------------------  | ---------------------|
| In same method | Y |
| One is found in a method in another service | Y |
| One is found in a transactional method in another service | Y |
| One is found in an asynchronous method in another service | **N** |

### Note
- The PersistenceContext in Spring is default to **PersistenceContextType.TRANSACTION** 
- Although the objects returned has the same reference, it still touch the database **twice**

## Test Auto Flush
### Update a user without save, then save a new user
#### The change to the user entity is flushed back to DB
```  
public void isFlush(String email) {
    User user =  findUserByEmail(email);
    user.setMobile(new Date().toLocaleString());

    User newUser = newUser();
    userRepository.save(newUser);
}
``` 
### Save a new user, then find and update a user without save
#### The change to the user entity is not flushed.
```
public void isFlush2(String email) {
    User newUser = newUser();
    userRepository.save(newUser);

    User user =  findUserByEmail(email);
    user.setMobile(new Date().toLocaleString());
}
```

### Same as above, but annotated with @Transactional
#### Invoke this method **inside** this class, the change to user entity will **not** be flushed
#### Invoke this method from **outside** the class, the change to user entity will be flushed
```
@Transactional
public void isFlush3(String email) {
    User newUser = newUser();
    userRepository.save(newUser);

    User user =  findUserByEmail(email);
    user.setMobile(new Date().toLocaleString());
}
```
### Note
- JpaRepository annotated all CRUD methods as @Transactional
