# COMPUTER DATABASE

# Architecture
## MODELE 
### Company
Variables 
* private id:Long
* private name:String

Methodes : getter & setter

### Computer
Variables 
* private id:Long
* private name:String
* private introduced:Timestamp
* private discontinued:Timestamp
* private Manufacturer:Company

Methodes : getter & setter

### ConnectionDB
Variables 
* private id:Connection

Methodes : getter & setter
* ArrayList<Computer> listComputer()
* ArrayList<Company> listCompany()
* Computer showComputerDetails(Long id)
* int createComputer(String name, Timestamp introduced, Timestamp discontinued, Long idManu)
* int updateComputer(Computer c)

## CONTROLLEUR

## VUE
### MainView

Methodes : 
* public static void main(String[] args)

# Fonctionnalité
* List computers 
* List companies
* Show computer details 
* Create a computer
* Update a computer
* Delete a computer