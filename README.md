# RSA-Gruppo6
Multi Client

# Membri del gruppo

Valerio Gallo
Selena Paniccià 
Ilario Perticari

# Classi da sviluppare

1. Server
2. Client
3. ServerThread
4. ClientThread

# Server Class:

Vengono generate le chiavi del server
ArrayList dei ServerThread che si hanno 
ArrayList delle chiave pubbliche dei vari Client

# Client Class:

Generazioni chiavi del client 
Assegnamento nome client 
Invio del messaggio ad un Client
Crittazione del messaggio con algoritmo RSA

# ServerThread Class:

Riceve il messaggio che manda il Client mittente e poi lo riamnda al Client destinatario

# ClientThread Class:

ricezione del messaggio mandato dal ServerThread
Decrittazione del messaggio e visualizzazione

