# Assignment_4_Software-Engineering-ISYS1118-

## Course Learning Outcome

### This Assessment support the following learning outcomes (CLO): 
* Explain how iterative software engineering processes can facilitate software development
* Evaluate requirements for a software system
* Communicate effectively with others, especially regarding the progress of the system development and the content of the design by means of reports and presentations. Use appropriate design, `version control and collaboration tools to work effectively as a team.
* Recognise and describe current trends in the area of software engineering. 
* Design and carry out tests using various testing techniques and tools.

## Overview of Assessment
This assignment will assess your ability in terms of writing quality unit tests, test cases, user stories, and acceptance tests, as well as working with GitHub and CI/CD pipelines using GitHub Actions. You will be asked to implement and test some small functions in the
 
**DigtialID** platform. Then you need to move your source code to GitHub. Also, you need to show that the GitHub Actions workflow is triggered to automatically run the Unit tests once code is pushed to GitHub. Furthermore, following the templates and structures taught in the lectures, you must write **eight user stories** for the **DigtialID** platform, along with their acceptance criteria. Finally, you need to make a video of your Java program.

## Assessment Activities

**<ins>General Description</ins>**
: Based on the requirements of the DigtialID platform, you need to do the following activities for the DigtialID platform.

### Activities in Assessment 4

Activity 1: Git/GitHub/GitHub Actions and Unit Test + a Video of your program (17 points): In the DigtialID platform, there is a class called Person. The Person class has three functions called "addPerson", "updatePersonalDetails" and "addID". You are asked to implement and test these three functions. The Person class has the following methods and attributes.
   
``` 
Important Note:

The code written below is just a placeholder. You are allowed to edit it and add new functions (e.g., add getter and setter, new constructors, etc.), new attributes, new classes, etc. You are completely FREE on how you want to implement and test these functions. The key point is to write enough test cases to test the conditions discussed below. You are free to consider any other conditions.
```

.addPerson function: This method stores information about a person in a TXT file. However, the following conditions should be considered when adding persons. If the Person's information meets the below conditions and any other conditions you may want to consider, the information should be inserted into a TXT file, and the addPerson function should return true. Otherwise, the information should not be inserted into the TXT file, and the addPerson function should return false.

Condition 1: personID should be exactly 10 characters long; the first two characters should be numbers between 2 and 9, there should be at least two special characters between characters 3 and 8, and the last two characters should be uppercase letters (A-Z). Example: "56s_d%&fAB"
Condition 2: The address of the Person should follow the following format: Street Number|Street|City|State|Country.  The State should be only Victoria. Example: 32|Highland Street|Melbourne|Victoria|Australia.
Condition 3: The format of the birthdate of the person should follow the following format: DD-MM-YYYY. Example: 15-11-1990


2. updatePersonalDetails function. This method allows updating a given person's ID, firstName, lastName, address and birthday in the TXT file. Changing personal details will not affect their demerit points or the suspension status. All relevant conditions discussed for the addPerson function also need to be considered and checked in the updatePersonalDetails function. If the Person's updated information meets the below conditions and any other conditions you may want to consider, the Person's information should be updated in the TXT file with the updated information, and the updatePersonalDetails function should return true. Otherwise, the Person's information should not be updated in the TXT file, and the updatePersonalDetails function should return false.

Condition 1: If a person is under 18, their address cannot be changed.
Condition 2: If a person's birthday is going to be changed, then no other personal detail (i.e, person's ID, firstName, lastName, address) can be changed.
Condition 3: If the first character/digit of a person's ID is an even number, then their ID cannot be changed.
In this activity, each team is requested to do the following activities:
Activity 1.1: Create a Maven-based Java project. You are free to use this pom.xml Download pom.xmlfile as a guide when creating the project.
Activity 1.1.1 (Unit Tests and Test Cases for three functions): Develop and write 15 test cases. Each function should have 5 test cases (use this template). Each test case should have at least one test data. You need to implement the unit tests for these THREE functions using JUnit that test the conditions (7.5 points in total: unit tests and test cases for each function have 2.5 points).

Activity 1.1.2 (The implementation of three functions in Java): Implement these THREE functions in Java that meet the conditions. You can use any Java IDEs (6 points in total: each function has 2 points).

Activity 1.2 (Presentation, GitHub, GitHub Action): Create a GitHub repository and move the implemented functions, their unit tests, and POM.XML to the GitHub repository.  Then, add a GitHub Actions workflow to the GitHub repository and automatically run tests using the GitHub Actions workflow. You are free to use this github-actions-demo.yml Download github-actions-demo.ymlas a guide. Also, record your screen while (1) running and testing your program on your local machine with the changes made to a TXT file/TXT files (1 minute), (2) running and testing your program using GitHub Actions (1 minute), and (3) showing the commits of each team member on GitHub (2 minutes) (3.5 points).

Recorded video characteristics: The recorded video should also show that your project is linked with the remote GitHub repository correctly.  No need to explain the content inside the pom.xml file and the github-actions-demo.yml file. You need to show how the GitHub Actions workflow is triggered when you push your code to the repository and the unit tests are automatically executed. Show that all tests have passed. The total recording should not be more than 4 minutes and 100 MB. The recorded video should have a 720p resolution. You need to upload the recorded video to Canvas as part of your submission. Alternatively, once you have created the video with SharePoint or MS Teams, you can insert the link to the recorded video in a WORD/PDF file and give your tutor access to the recorded video. The WORD/PDF file should be uploaded as part of your submission to Canvas. 

Note 1. There is no need to have your face visible in the recorded video.

Note 2. There is no need for all team members to talk in the recorded video. This can be done by one member. It is fine if all team members want to talk in the recorded video.

Note 3. You can use a TXT file or multiple TXT files to implement and test the Person class.  The data in the TXT file(s) should be readable. It is up to you how you want to structure the data in the TXT files. There is no need to store the post's information in a Database Management System like SQL Server, MySQL, Oracle, etc. 

Note 4. You MUST write enough meaningful comments to explain your code in JUnit 5 and Java.

Note 5. You CAN develop your code (implementing functions and JUnit test code) in any Java IDEs such as Eclipse, VS Code, IntelliJ and use JUnit for tests.

Note 6. You need to develop a Maven Java project. There is no need to add a Graphical User Interface, a menu, or even the main function to your program (to interact with end-users).

Note 7. The code written above is just a placeholder. You are allowed to edit it and add new functions (e.g., add getter and setter, new constructors, etc.) and new attributes. You are completely FREE on how you want to implement and test these functions. The key point is to write enough test cases to test the conditions. You are free to consider any other conditions. 

Note 8. You only need to write test cases explicitly to test the above mentioned conditions. There is no need to test other conditions. However, if you want to write test cases for any other conditions, feel free to do so.

Activity 2: User Story and Acceptance Criteria (8 points): Based on the requirements collected for the DigitalID platform, each team is asked to write EIGHT user stories and THREE acceptance criteria for each user story. Each user story and its acceptance criteria have 1 point. In total, you need to write 8 user stories and 24 acceptance criteria. The user stories and acceptance criteria should be written based on the template and principles taught in Week 8. See the template. 


4. Submission Instructions & Feedback
As this assignment is team-based, each team must submit a report that includes all activities described above. The team needs to upload the contribution form, along with other documents. The grade of each team member is determined based on their level of contribution. Each team member is expected to contribute equally to this team-based assignment. Click here to download the Statement of Contributions form.

Submission Type: A Zip file includes the following items:

1. A written report that contains (a) Test Cases developed for testing the functions and (b) User Stories and Acceptance Criteria of the User Stories.

2. The whole Java project that includes the implementation of the specified functions and Unit Tests

3. A recorded video (4 minutes, 720p resolution, 100 MB) or a WORD/PDF file that includes a link to the recorded video (give access to ONLY your tutor to watch this video)

4. Completed the contribution form

Note: It is acceptable to put your names in the signature part of the contribution statement form. There is NO need to do a physical or digital signature.
Report Length: The criteria are in the correctness and completeness of the artefacts in the report.

Late submission: Assignments received late and without prior extension approval or special consideration will be penalised by a deduction of 10% of the total score possible per calendar day late for that assessment


5. Required Software Tools
For Activity 1, You can develop your code (the implementation of functions and JUnit test code) in any Java IDEs such as Eclipse and IntelliJ and use JUnit for tests.
For Activity 2, you can use MS Word.
6. Important Notes
You can get ideas and learn from all online resources for Assignments. It is your responsibility to ensure that your submission is original and solely your own work. Otherwise, you will lose some marks.
You need to work on Assignment 4 as part of a group. You are not allowed to submit Assignment 4 individually. The individual submission in Assignment 4 will NOT be marked (Zero Mark).

Extension requests should be made one working day before the deadline. 
Extensions are for individuals, not per group. In other words, if a member of a group gets an extension, the group should submit the assignment by the deadline, and that member should submit the updated version of the assignment to me via email. I will check the differences between the original version and the updated version.