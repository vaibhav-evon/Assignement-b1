#  Assignnme

The project contains only jpa some models used in a springboot 2.7 project with JDK 17.
These models need to be used in new entities and therefore you need to carry out the following tasks:

 - Create an ER diagram for the provided entities in the package ai.budding.models.jpa;
 - add the following entities to it
   - Grade (title, descritpion, craetedOn, modifiedOn)
   - Section (grade, title, descritpion, craetedOn, modifiedOn)
   - Institution (title, descritpion, craetedOn, modifiedOn)
   - role (User, role ENUM{Teacher,Student,Admin})
- add the following relations
  - A virttual class belongs to a single grade
  - A section belongs to a single grade 
  - A student belongs to single section
  - An institution has multiple grades with different sections
  - An institution has multiple teachers
  - An institution has one or more admins
Make the project work within a 2.7 springboot framework and craete controllers for CRUD endpoints for all new entities


# Deliverable

- checkin code to provided repo
- project should compile with java 17 and springboot 2.7
- example code to use CRUD endpoints for all controller endpoints created

 
