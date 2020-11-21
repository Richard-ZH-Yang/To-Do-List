# To Do List

## What will the application do?
The name of the application is to-do list. It is inspired by *Microsoft to-do*, and there are some innovations to further
improve user's experience. The to-do List has **four default list**, which are
 - My Day
 - Important
 - Planned 
 - Tasks

And user can also add their customized list to contain their *to-do tasks*. User can add and delete the customized list,
but cannot delete or add default list. There are some functions that appear both in customized list and default list in 
the future, which are:
- sort the list
- add task
- delete task
- edit task
- complete a task
- undo complete a task
- display all the task information
- hide complete tasks(in future phases)

Each lists contain two sections, one being the unfinished tasks and other being completed tasks.


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

###Phase 4:
####Phase4: Task2:
I choose to implement the first option which is to design a robust class. In my Task class inside the model package, 
I made the setDueDay method able to throw InvalidDateException when the due date and today's date difference is
greater than 10 years. I catch this exception efficiently in my UI classes. 


####Phase4: Task3:
If I had more time to work on this project, I would first remove the association from Console and TaskListEditor to BasicList
class. As BasicList is part of ToDoListProgram, and it is redundant to make the ui class have association with BasicList.
It will also create possible coupling as when changing either in ToDoListProgram or BasicList directly, other class
must change simultaneously. 

Another change I would make is that make TaskListEditor has association with TaskTableModel with multiplicity of 
1 if only implement uncompleted task table, 2 if implement both uncompleted task table and completed task table in
the future.

Moreover, I would make ToDoListProgram only has one List of BasicList and BasicList only has one List of Task. When I 
first make this program, I was planning to separate the tasks into completed task and uncompleted task, but it is 
enough too only make the Task class has one field named isImportant. And I also planed to separate default list module 
and customized module. But it is really not necessary to have two lists of that.

To add more features, I would make a bidirectional association between TaskListEditor and Tool. I would create 
a feature for the current tool, which user could use the keyboard input like ENTER to keep using this tool.

Finally, I would finish implementing the TaskEditor GUI to have the same feature as Console UI. In current stage, my
GUI can only focus on one BasicList, but it should have contained a list of BasicList, and user could do operations 
about these lists.


















