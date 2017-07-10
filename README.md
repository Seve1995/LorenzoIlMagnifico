![Header](https://image.ibb.co/gGT67v/Header.png)

# Project Template - Prova Finale (Ingegneria del Software)

Group 22 is composed by: 
Matteo Petrini,
Fabio Piazza,
Giuseppe Severino

## How to run the program: 
The main classes are:

CLIENT: "Client" class, in package "client"

SERVER: "Game Server" class, in package "connection"

There are no particular parameters to run the game.

## How to login/register
Once the client ask for a login or a registration, you can use one of these defaults users to login:

1) Username: "Matteo" , Password: "Petrini"
2) Username: "Fabio", Password: "Piazza"
3) Username: "Giuseppe", Password: "Severino"

Alternately, you can create your personal username and password by pressing/writing "Register".

## How to play the game
Every user, in the first screen, has to choose between RMI/SOCKET connection
and between GU/CL interface. 
If the GUI is the choice other screen will appear, and a user can signUp or login in our server.
After that a user can create a match, join to an existing match or find a random match.
After some time the main screen should open. There is the gameboard (shared among all the players of a 
single match) and the personal player board.
So from now if it is your turn (take care of the notification bottom right)
you can do some actions. Please notice that leaders action should be done before setting familiar action.
To do an action select the "subject" of that action (leader card or familiar) and then press the button corresponding 
to the action you want to do. You can click also one card on the tower. Then press confirm. A feedback should appear 
on the notification area. 
To perform a leader action, just click on a leader card, then press "discard", "play" or "active".
When it is your turn you can pass (you will lose that action) or you can exit the game (you will be suspended).
The screen "Creation match" should appear.
At the end of the game should appear the final window informing you about the winner, and the global ranking of our server.

Playing on CLI allows you to do the same things described above. All the strings you have to write on the command line 
are exampled and explained in the terminal itself. The only difference is that you can type "show board" in every moment
to see a representation of the player board as a string. 

Additional persistence information: when a user press (or write, in the case of CLI) "Exit", he has to wait the end of 
other players' turn before being disconnected. 

## GUI Small tutorial
![The game's main screen](https://image.ibb.co/kZ0xEa/Game_Board_Tutorial.png)
In the above image is represented the game's main screen. 
When a player want to place a family member in the gameboard, he has to press one button from (1).
Then has to select one zone in the gameboard image (2) by pressing it, and finally he confirm the action by pressing the button "Confirm" (3).

## Some other infos
On the console of the server you can type the key word "exit". In so doing 
the server should stop itself. All the matches should be stopped, as well, and, when the server will be powered on again
all the matches should be restored.

The UML representation of the whole project is contained in resources/uml/
