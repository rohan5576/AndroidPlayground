# Abstraction vs Interface in Kotlin

##  Abstraction (using `abstract` class)

- **Abstraction** is the process of hiding implementation details and showing only essential features.
- It is implemented using an `abstract class`.

### Key Points:

- An abstract class can have both **abstract methods** (without body) and **concrete methods** (with body).
- It **can have constructors** and maintain **state** using variables with backing fields.
- A class can **extend only one abstract class** (single inheritance).
- Use when you want to provide **common base functionality** to multiple **related** classes.

---

##  Interface

- An **interface** defines a contract that a class must follow.

### Key Points:

- It can have **abstract methods** and **default (concrete) methods**.
- Interfaces **cannot have constructors** and **cannot hold state** (only `val` without backing fields).
- A class can **implement multiple interfaces** (supports multiple inheritance).
- Use interfaces to define **common behavior** across **unrelated** classes.