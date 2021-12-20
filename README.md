<div id="top"></div>

<!-- PROJECT LOGO -->
<br />
<div align="center">

<h3 align="center">RSAMultiClient</h3>

  <p align="center">
    Applicazione multi client che permette di scambiare messaggi tra vari client, usando la crittografia RSA.
  </p>
</div>



<!-- TABELLA DEI CONTENUTI -->
<details>
  <summary>Tabella dei contenuti</summary>
  <ol>
    <li>
      <a href="#riguardo-il-progetto">Riguardo il progetto</a>
      <ul>
        <li><a href="#sviluppato-con">Costruito con</a></li>
      </ul>
    </li>
    <li><a href="#documentazione-classi-java">Documentazione classi</a></li>
    <li><a href="#uso-del-programma">Uso del programma</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contatti">Contatti</a></li>
  </ol>
</details>



<!-- RIGUARDO IL PROGETTO -->
## Riguardo il progetto

Il progetto è stato richiesto dalla professoressa Maria Rita Di Berardini, e sviluppato dal gruppo 6, composto da Valerio Gallo, Ilario Perticari e Selena Paniccià.
L'applicazione funziona come dovrebbe, ci sono miglioramenti possibili, come l'aggiunta di altri controlli, la possibilità di sapere quali utenti sono connessi alla piattaforma,
l'implementazione di un login con username e password.

<p align="right">(<a href="#top">back to top</a>)</p>

### Sviluppato con

* [Java](https://www.oracle.com/it/java/)


<p align="right">(<a href="#top">back to top</a>)</p>

<!-- DOCUMENTAZIONE CLASSI JAVA -->
## Documentazione classi Java
- [✔] Server
    - [✔] Accettazione dei client avviati, inizializzazione e collezionamento dei thread associati al server in una lista.
- [✔] ServerThread
    - [✔] Collezionamento in una lista di tutte le chiavi pubbliche dei client avviati.
    - [✔] Metodo costruttore del ServerThread.
    - [✔] Controllo di client con nomi uguali.
    - [✔] Invio al client mittente della chiave pubblica del client ricevente.
    - [✔] Invio al client ricevente del messaggio criptato dal client mittente.
- [✔] Client
    - [✔] Generazione delle chiavi per la crittografia RSA.
    - [✔] Invio al server della chiave pubblica.
    - [✔] Inserimento di un nome per ogni client.
    - [✔] Inserimento del nome del client ricevente.
    - [✔] Crittazione del messaggio con la chiave pubblica del client ricevente.
- [✔] ClientThread
    - [✔] Classe utilizzata generalmente per la ricezione dei dati mandati dal server.
    - [✔] Ricezione delle chiavi del client ricevente e invio alla classe madre Client.
    - [✔] Invio alla classe madre Client del risultato dei controlli.
    - [✔] Decrittazione del messaggio inviato dal client mittente attraverso la chiave privata del client ricevente, condivisa con il thread associato al client attraverso il metodo costruttore.
- [✔] Keys
    - [✔] Metodo costruttore della lista di chiavi pubbliche memorizzata all'interno del server.


<p align="right">(<a href="#top">back to top</a>)</p>

<!-- USO DEL PROGRAMMA -->
## Uso del programma

Per far funzionare il programma, una volta avviati i client, vanno inseriti prima i nomi in ciasciun client, poi una volta inseriti i nomi, vanno
inseriti i nomi dei destinatari(case sensitive), poi è possibile scambiare messaggi correttamente, ci vorranno circa 5 secondi per la ricezione
del messaggio da parte del client ricevente, a causa del grande numero di cifre delle chiavi utilizzate.

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- ROADMAP -->
## Roadmap

- [✔] Gestione di più client.
    - [✔] Ricezione del messaggio da un client specifico richiesto dall'utente.
- [✔] Crittografia RSA.
    - [✔] Condivisione delle chiavi pubbliche.
- [✘] Controlli.
    - [✔] Controllo di client con nomi uguali.
    - [✘] Controllo che un client stia scrivendo a se stesso.
    - [✘] Controllo che si stia provando a scrivere ad un client non esistente.        

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- CONTATTI -->
## Contatti

Valerio Gallo - [GitHub](https://github.com/Vallozz) - gallo.valerio@istitutomontani.edu.it

Link alla Repository: [RSAMultiClient](https://github.com/ilario1/RSA-Gruppo6)

<p align="right">(<a href="#top">back to top</a>)</p>
