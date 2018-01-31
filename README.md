
# Java-RMI-Distributed-University-Assessment-System

Java RMI based Assessment System for the university. 

The server allows clients to authenticate and download an Assessment object. 

The Assessment object implements an interface that provides methods to retrieve and answer a list of multiple-choice questions.

The Assessment is completed on the client and the updated Assessment object can then be submitted back to the server for correction. 
The following interfaces are defined : 

ExamServer - this (remote) interface provides methods for user authentication, download of assessments and the submission of completed assessments.  Assessments can only be downloaded and submitted during certain time intervals while they are available on the server.

Assessment - this (serializable) interface provides methods for the retrieval of information about the assessment, and the retrieval / answering of questions.  It also has a method to output the selected answer to each question - the answer provided to a question can then be changed, if desired, prior to submission of the assessment. A completed assignment may be submitted multiple time to the server up to the closing time. The last version submitted would then be corrected.

How to run  : 

java -cp /Users/macbook/rmidemo -Djava.rmi.server.codebase=file:/Users/macbook/rmidemo/ -Djava.rmi.server.hostname=localhost -Djava.security.policy=server.policy ct414/ExamEngine

