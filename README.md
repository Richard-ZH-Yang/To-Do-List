# To Do List

## What will the application do?
To-Do-List gives you the confidence that everything’s organized and accounted for, so you can make progress on the things 
that are important to you.It has both a console version as well as a GUI version. And it is implemented with advanced design
patterns such as Observable pattern and Iterator pattern to improve the user experience. Features in this program including:

- load data from disk
- save data to disk in JSON format
- add list
- delete list
- edit list
- sort list
- add task
- delete task
- edit task
- complete a task
- undo complete a task
- sort a list based on task
- display all the task information
- hide complete tasks(in future phases)


## Who will use it?
to-do List is quite useful to keep track of the upcoming task and list the things that need to be done. 
From my own perspective, I find to-do list is quite useful to maintain a high productivity. 
And the to-do list is for **everyone**, it can be used in different scenarios and by different groups of people. 
But I believe **students** can make the most use of it, to record their assignment due date and things to study.


## Why is this project of interest to me?
I am reasonably passionate about these projects because of the practicability of the to-do list. Another reason is 
that the market for the to-do list application is enormous, as it is an application for everyone. 
I think it is an excellent opportunity to code a to-do list application by myself. Finally, 
I can demonstrate the concept of polymorphism of Java in this application smoothly, and therefore can further improve
 smy programming skill.  

## User Stories
**Phase 1 user stories:**
- As a user, I want to be able to add a task to my to-do list
- As a user, I want to be able to view the list of tasks from a specific task list
- As a user, I want to be able to delete a task from my to-do list
- As a user, I want to be able to mark a task as complete on my to-do list


**Phase 2 user stories:**
- As a user, I want to be able to save my to-do list to file
- As a user, I want to be able to be able to load my to-do list from file 
- As a user, when I select the quit option from the application menu, I want to be reminded to save my to-do list 
to file and have the option to do so or not.
- As a user, when I start the application, I want to be given the option to load my to-do list from file.

### Phase 4:

#### Phase 4 Task 2:
This program is quite robust, and is ready to handle every exception. In my Task class inside the model package, 
I made the setDueDay method able to throw InvalidDateException when the due date and today's date difference is
greater than 10 years. And this exception will be caught efficiently in my UI classes. 


#### Phase 4 Task 3:
In the future, first change I would make is that make TaskListEditor has association with TaskTableModel with multiplicity of 
1 if only implement uncompleted task table, 2 if implement both uncompleted task table and completed task table in
the future.

To add more features, I would make a bidirectional association between TaskListEditor and Tool. I would create 
a feature for the current tool, which user could use the keyboard input like ENTER to keep using this tool.

Finally, I would finish implementing the TaskEditor GUI to have the same feature as Console UI. In current stage, my    
GUI can only focus on one BasicList, but it should have contained a list of BasicList, and user could do operations 
about these lists.


















