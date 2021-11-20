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

1. Vengono generate le chiavi del server
2. ArrayList dei ServerThread che si hanno 
3. ArrayList delle chiave pubbliche dei vari Client

# Client Class:

1. Generazioni chiavi del client 
2. Assegnamento nome client 
3. Invio del messaggio ad un Client
4. Crittazione del messaggio con algoritmo RSA

# ServerThread Class:

5. Riceve il messaggio che manda il Client mittente e poi lo riamnda al Client destinatario

# ClientThread Class:

6. ricezione del messaggio mandato dal ServerThread
7. Decrittazione del messaggio e visualizzazione

