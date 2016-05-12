# space-expedition

<table border=0>
<tr>
<td>
Projeto para criar expedições em outros planetas e enviar robos (sondas) para mapeamento de território
<br>
<br>
Os comandos para criar expedições, deploy de robos e movimento utilizam chamadas REST
</td>
<td>
<p style="text-align: right"><img src="http://cdn.graphicsfactory.com/clip-art/image_files/image/9/746749-offroader.gif"/></p>
</td>
</table>

**Operação**

Operação precisa executar basicamente 3 comandos  

* Criar uma expedição com os limites do território
* Enviar os robos informando a posição no terriório e a posição N, S, L ou O
* Enviar comando para movimentar o robo

**Regras**
* Não podem existir expedições com o mesmo nome
* Não podem existir robos com o mesmo nome em uma expedição
* Robos não podem aterrizar no mesmo local de um robo já aterrizado
* Movimentos dos robos não podem ultrapassar os limites da expedição

# Instalação #
Projeto está configurado com Maven, efetuar a atualização e instalação pelo Maven

# Execução #
Execute o método Main da classe *org.destro.space.SpaceApplication*

SpringBoot subirá um embeded TOMCAT na porta 8080

*http://localhost:8080*

# API #
* * *
####[API] Criar uma expedição####

Cria uma nova expedição informando o tamanho do território a ser mapeado

#######URL#######
/createExpedition

#####Method#####
POST
  
#####Parameters#####
* String name `required`
* Integer borderX `required`
* Integer borderY `required`

#####Return#####
* JSON

#####Response#####
* * *
##[API] Enviar um robo para expedição##

Efetua deploy de um novo robo para expedição

#####URL#####
/deployRobot/:expeditionName

#####Method#####
POST
  
#####Parameters#####
* String expeditionName `required`
* String name `required`
* Integer landX `required`
* Integer landY `required`
* String direction `required`

#####Return#####
* JSON

#####Response#####
* * *
##[API] Movimentar um robo em expedição##

Movimenta robo de acordo com os comandos abaixo:

L gira o robo 90° para esquerda  
R gira o robo 90° para direito  
M move o robo  

#####URL#####
/moveRobot/:expeditionName/:robotName

#####Method#####
POST
  
#####Parameters#####
* String expeditionName `required`
* String robotName `required`
* String commands `required`

#####Return#####
* JSON

#####Response#####
* * *
##[API] Receber todas as expedições##

Retorna todas as expedições

#####URL#####
/allExpeditions

#####Method#####
GET
  
#####Parameters#####
* None

#####Return#####
* JSON

#####Response#####
* * *

# Tabela com erros de validação #

| CODE | Message |
| ---- | ------- |
| 100  | Name Already exists |
| 200  | Out of border |
| 300  | Not allowed to land |
| 400  | Invalid Direction |
| 500  | Name Already exists |
| 600  | Invalid command |
