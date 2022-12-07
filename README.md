# Project 1 Repository
*First push attempt*

Completed Requirements:
1) Can use a username and password to log in.
2) Can register a new account with username and password.
3) Will notify the user if the username is unavailable.
4) Can submit new reimbursement tickets.
5) Reimbursement ticket author provides a description and amount during submission.
6) Pending tickets are in a queue/list that can only be seen by managers.
7) Tickets can be processed (approved/denied) by managers.
8) Employees can see a list of their previous submissions

Completed Stretch-Goals:
1) Employees can put a reimbursement type.
2) Managers can change other users' roles.
3) Users can add their name and address.

Added Extras:
1) Users can signout.
2) Can view who is currently logged in.
3) Can view tickets based on status.
4) Managers can view all users.


How To:
Login
GET http://localhost:8085/login (port may vary)
{
    "email": "",
    "password": ""
}


Create New User
POST http://localhost:8085/newuser (port may vary)
{
    "email": "",
    "password": "",
    "firstName": "",
    "lastName": "",
    "address": ""
}


Signout
GET http://localhost:8085/signout (port may vary)


View Who Is Currently Logged In
GET http://localhost:8085/session (port may vary)


Promote Employee to Manager (Only able to if Manager)
PATCH http://localhost:8085/promote (port may vary)
{
    "id": ,
    "email": "",
    "role": ""
}


View All Users (Only able to if Manager)
GET http://localhost:8085/users (port may vary)


Create New Ticket
POST http://localhost:8085/createticket port may vary)
{
    "reimbursementType": "",
    "amount": ,
    "description": ""
}


Check My Tickets (views all)
GET http://localhost:8085/mytickets (port may vary)


View My Pending Tickets
GET http://localhost:8085/pendingtickets (port may vary)


View My Approved Tickets
GET http://localhost:8085/approvedtickets (port may vary)


View My Declined Tickets
GET http://localhost:8085/declinedtickets (port may vary)


See All Tickets (Only able to if Manager)
GET http://localhost:8085/alltickets


Make Decisions On Pending Tickets (Only able to if Manager)
PATCH http://localhost:8085/decideonticket (port may vary)
{
    "ticketid": ,
    "status": ""
}