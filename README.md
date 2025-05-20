# SE_SQC_Project
College Software Engineering Project: For analysts documenting functional requirements with scenarios, our SQC app will provide quantitative information and enable detection of problems in functional requirements written in the form of scenarios. The app will be available via GUI and also as a remote API, thanks to which it can be integrated with existing tools.<br/><br/>

Format of scenarios to be put into the Readme.md project:
* The scenario includes a header specifying its title and actors (external and system)
* The scenario consists of steps (each step contains text)
* Steps can contain sub-scenarios (any level of nesting)
* The steps may start with the keywords IF, ELSE, FOR EACH

Example:
Title: Book addition
Actors:  Librarian
System actor: System

* Librarian selects options to add a new book item
* A form is displayed.
* Librarian provides the details of the book.
* IF: Librarian wishes to add copies of the book
  * Librarian chooses to define instances
  * System presents defined instances
  * FOR EACH: instance:
    * Librarian chooses to add an instance
    * System prompts to enter the instance details
    * Librarian enters the instance details and confirms them.
    * System informs about the correct addition of an instance and presents the updated list of instances.
* Librarian confirms book addition.
* System informs about the correct addition of the book.



Project Management Details:
The project is managed using github Issues and github Projects in an agile way respecting the rules of Scrum.
Every Product Backlog Item is entered as an Issue and is divided into smaller tasks when being selected to current sprint.
Some side tasks (mainly management tasks), which aren't in the Product Backlog, are also entered to Issues.
The project uses milestones to denote which tasks are part of a given sprint and which haven't still been chosen to any sprint and still belong to the Product Backlog.
It also uses labels to denote importance, labour intensity and type of a task. Importance is given only to the high-level tasks meaning that subtasks aren't assigned importance.
The github Project consists of 5 columns:
* Product Backlog Items which still haven't been chosen to any sprint
* Tasks which are part of the current sprint but hasn't been started yet
* Tasks belonging to current sprint that are in progress
* Finished tasks belonging to current sprint
* Tasks which were done in previous sprints
