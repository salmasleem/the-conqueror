# TheConqueror

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/fedd9f7938f14125ae98f4c33dd8d0d7)](https://app.codacy.com/gh/aboueleyes/the-conqueror?utm_source=github.com&utm_medium=referral&utm_content=aboueleyes/the-conqueror&utm_campaign=Badge_Grade_Settings)

A single player turn-based empire building game. A player
initially chooses one historical city to start his empire with. The goal is to conquer the whole
world by taking control over every other city under a certain amount of turns. In order to
achieve this goal, the player have the option of building various types of building in any city he has control over and also build armies in order to conquer other cities.

## Contents 
1. OOP concepts (Inheritance - Polymorphism - Abstraction - Encapsulation ).
2. Exception Handling.
3. GUI 

### Who is this game for
  - For anyone who wants to learn about OOP, MVC, Swing. 
  - For anyone who might find this game fun :relieved:.
  - For my future employer who will accept my resume when seeing this wonderful clean code game :"D.
## Project Structure
<details>
    
```bash
src/
├── buildings
│   ├── ArcheryRange.java
│   ├── Barracks.java
│   ├── Building.java
│   ├── EconomicBuilding.java
│   ├── Market.java
│   └── ..............
├── controllers
│   └── Controller.java
├── engine
│   ├── City.java
│   ├── Distance.java
│   ├── Game.java
│   ├── Player.java
│   └── ............
├── exceptions
│   ├── ArmyException.java
│   ├── BuildingException.java
│   ├── FriendlyCityException.java
│   ├── FriendlyFireException.java
│   └── .......................
├── units
│   ├── Archer.java
│   ├── Army.java
│   ├── Infantry.java
│   ├── Status.java
│   └── ................
├── utlis
│   └── ReadingCSVFile.java
└── views
    ├── button
    │   ├── CityButton.java
    │   ├── StyledButton.java
    │   └── UnitButton.java
    ├── MyInputVerifier.java
    ├── panel
    │   ├── ArmyPanel.java
    │   ├── CardsPanel.java
    │   ├── MilitaryBuildingPanel.java
    │   ├── PlayerPanel.java
    │   └── .....................
    ├── RXCardLayout.java
    └── view
        ├── BattleView.java
        ├── CityView.java
        ├── EndGameView.java
        ├── StartView.java
        └── ..............

```
</details>

## Views Of the game 
  
the game consists of 3 views beside the start view 

1) <strong> World Map View </strong> 
 
 
   <img src = "assets/img/views/worldMapView.jpeg" >
  
   responsible for viewing the cities of the game , armies of the player and thier status.

2) <strong> City View </strong>


   <img src = "assets/img/views/cityView.jpeg">
   
   responsible for showing different buildings for each city where the player could build , upgrade and recruit units ti build his/her army.


3) <strong> Battle View </strong> 


   <img src = "acl-fogration-fe/public/images/Admin%20add%20instructor.JPG"> 
   
   responsible for battles between army of the player and opponent cities where player could manage the battle manually or choose to auto resolve it.
 

## Authors 
1. [Shimaa Ahmed](https://github.com/ShimaaBetah)
2. [Ahmed Shaawray](https://github.com/shaarawy29)
3. [Ibrahim Abou Elenein](https://github.com/aboueleyes)

