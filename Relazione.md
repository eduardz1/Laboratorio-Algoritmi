# Relazione Progetto di Algoritmi e Strutture Dati

// Work in progress

## Esercizio 1

Per eseguire è necessario specificare, in quest' ordine, specificare all'eseguibile "ex1' il path del file dati e il numero di records che si vogliono sortare. È possibile aggiungendo a tempo di compilazione la macro "-DPRINT_RECORDS" stampare a terminale i records sorted e unsorted

### Quick Sort

Il `quick_sort()` è un algoritmo che ordina una collezione partendo da un pivot, il pivot può essere scelto in vari modi, e in base a quale viene scelto il tempo di sorting varia. Il `quick_sort()` utilizza `_part()` per scegliere il pivot prima di chiamare `partition()` per dividere gli elementi del range selezionato in un sottoinsieme di elementi maggiori e uno di elementi minori del pivot la cui posizione finale viene restituita dal metodo.

#### Impatto della scelta del pivot nel quick sort

La chiamata a `rand()` porta il `quick_sort()` con pivot scelto randomicamente o come mediana di tre numeri ad essere mediamente più lento rispetto agli altri 3 casi presi in considerazione.
La tabella sottostante riporta il tempo impiegato ad ordinare un array di 20kk elementi di tipo `struct Record`

| RANDOM | RIGHT | LEFT | MIDDLE | MEDIAN3 |
|:------:|:-----:|:----:|:------:|:-------:|
|

La scelta del pivot diventa importante quando l'array in input risulta già parzialmente o totalmente ordinato.
La tabella sottostante riporta il tempo impiegato da `quick_sort()` per scorrere un array già ordinato di 200k elementi

| RANDOM | RIGHT | LEFT | MIDDLE | MEDIAN3 |
|:------:|:-----:|:----:|:------:|:-------:|
|

#### Fallback a binary_insert_sort()

Quando il `quick_sort()` lavora su un range sufficientemente piccolo il `binary_insert-sort()` lavora più velocemente, a tempo di compilazione è possibile, aggiungendo la define "-DFALLBACK_BIS", abilitare tale ottimizzazione. Il range minimo è stato impostato empiricamente a 1000 elementi.

### Binary Insertion Sort

Essendo un algoritmo di complessità O(n^2) non ci aspettiamo finisca in tempi sensati l'ordinamento dei 20kk records, facendo due calcoli sui nostri computer dovrebbe metterci difatti circa 2 anni.

## Esercizio 2
