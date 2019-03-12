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
* private computers:HashMap<Long, Computer>
* private companies:HashMap<Long, Company>

Methodes : getter & setter
* HashMap<Long, Computer> listComputer()
* HashMap<Long, Company> listCompany()
* Computer showComputerDetails(Long id)
* Computer createComputer(String name, Timestamp introduced, Timestamp discontinued, Long idManu)
* Computer updateComputer(Computer c)

## CONTROLLEUR

## VUE
### MainView
Variables
* conn:ConnectionDB

Methodes : 
* public static void main(String[] args)

# Fonctionnalité
* List computers 
* List companies
* Show computer details 
* Create a computer
* Update a computer
* Delete a computer