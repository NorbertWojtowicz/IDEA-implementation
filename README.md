# Projekt - Algorytm kryptograficzny IDEA
**IDEA – International Data Encryption Algorithm** jest symetrycznym (względem  klucza) algorytmem  szyfrowania  blokowego  przeznaczonym do szyfrowania  tekstu  do nieczytelnego  dla  ludzi  formatu, taki  zaszyfrowany  tekst  można z łatwością  I bezpiecznie  przesyłać  przez internet.
W teorii  algorytm  może  służyć  także do zaszyfrowania  innych  rzeczy  typu  pliki, ale w swojej  implementacji  skupiłem  się  na  szyfrowaniu  tekstu – zgodnie z poleceniem.
Powstał  na  uniwersytecie ETH w Zurichu, a pierwotna  jego  implementacja  została  przedstawiona po raz  pierwszy w 1991 roku  przez  dwóch  autorów: Xueji’a Lai oraz Jamesa Masseya. Finalna  nazwa  została  przyjęta  dopiero w 1992 roku, wcześniej  proponowano  ‘Proposed Encryption Standard (PES)’,  czy  ‘Improved Proposed Encryption Standard (IPES)’,  słowo improved w  ostatniej  nazwie  wynika z  dosłownie  ulepszenia  poprzednika o  odporność na kryptoanalizę  różnicową.

## Czym  dokładnie jest szyfrowanie  blokowe?
Poprzez  szyfrowanie  blokowe  rozumiemy  szyfrowanie  danych  podzielonych  na  mniejsze  bloki. Dzielimy  zarówno  dane  które  chcemy  zaszyfrować jak I klucz  który do tego  celu  będzie  użyty. W przypadku  algorytmu IDEA typowy  rozmiar  bloku to 128 bitów, gdzie 64 bity to dane  wejściowe, a pozostałe 64 bity to zaszyfrowane  dane  wyjściowe. Rozmiar  bloku  generalnie  odnosi  się do ilości  bitów  które  są  przetwarzane  jednocześnie, jako  jedność.

## Bezpieczeństwo  oparte  na  kluczu
Algorytm IDEA opiera  swoje  bezpieczeństwo  na  sekretnym  kluczu, więc  nawet  znajomość  dokładnej  implementacji  nie ma znaczenia  przy  próbie  deszyfracji, wymagany jest do tego  klucz.  Klucz  zbudowany jest z 128 bitów, ze względów  bezpieczeństwa w swojej  implementacji  algorytmu  wymusiłem  klucze  tylko o takim  rozmiarze.  Jeden z procesów  algorytmu  dzieli  owy  klucz  na 52 16-bitowe podklucze  wykorzystywane  podczas  szyfrowania w następujący  sposób:

-   Pierwsze 8 podkluczy  wyciągane jest bezpośrednio z oryginalnego  klucza
    
-   Następnie  klucz  przesuwany jest o 25 bitów w lewo (w sposób “Circular” - obrót  bitowy)
    
-   Wyciągamy  kolejne 8 podkluczy z utworzonego w poprzednim  kroku  klucza
    
-   Wracamy do kroku  drugiego, aż do utworzenia 52 podkluczy

## Generowanie  podkluczy  deszyfrujących
Każdy z 52 16-bitowych podkluczy  deszyfrujących jest odwrotnością  podkluczy  używanych  podczas  szyfrowania w odniesieniu do zasosowanych  operacji  algebraicznych. W mojej  implementacji  rozwiązałem ten problem całkiem  sprytnie, używając  operacji  dopełnien do dwójki I odwracając  niektóre  z podkluczy.

## Dzielenie  bloków  wejściowych
Każdy 64-bitowy blok  danych  wejściowych  dzielony jest na  cztery 16-bitowe podbloki (oznaczone  jako X1 – X4). Dla  jasności - jeśli  dane  wejściowe  wynosza  więcej  niż 64 bity, szyfrowanych  będzie  więcej  bloków, np. Dla 300-bitowych danych  stworzonych  zostanie 5 64-bitowych bloków (ostatni  wypełniony  zerami do pełnej  wartości 64 bitów).

## Proces szyfrowania
Gdy  mamy  już  wygenerowane  klucze  szyfrujące  zgodnie z punktem  wyżej, przystępujemy do procesu  szyfrowania. Składa  się on z 8 identycznych  rund (ang. ‘round’), każda z nich ma 14 operacji I używa 6 podkluczy  które  są  mieszane z blokami do szyfrowania. Po tym  następuje  jeszcze  jedna  transformacja  wyjściowa  używająca  już  tylko 4 podkluczy. (Każda z 8 rund po 6 podkluczy: 6*8=48 plus 4 podklucze  dla  transformacji  wyjściowej, stąd  wymóg  52 podkluczy)  Wszystkie  operacje  składające  się  na  pojedynczą  rundę  zostaną  wyjaśnione w punkcie  poniżej, w zapisie  matematycznym.

## Proces odszyfrowywania
Proces odszyfrowywania jest dokładnie  taki  sam jak proces  szyfrowania z jedną  istotną  różnicą, używa  kluczy  deszyfrujących  wygenerowanych  zgodnie z punktem  wyżej.

## Proces szyfrowania w zapisie  matematycznym

Z1-Z6 oznaczają  podklucze  dla  poszczególnej  rundy, natomiast X1-X4 oznaczają  podbloki 64-bitowego bloku  wejściowego

Każda z 8 rund  wygląda w następujący  sposób:

1.  X1 mnożymy modulo 2^16 + 1 przez Z1 
2.  Dodajemy X2 modulo 2^16 do Z2 
3.  Dodajemy X3 modulo 2^16 do Z3
4.  X4 mnożymy modulo 2^16 + 1przez Z4
5.  Wyniki  operacji 1 i 3 poddajemy  operacji XOR-owania
6.  Wyniki  operacji 2 i 4 poddajemy  operacji XOR-owania
7.  Wynik  operacji 5 mnożymy modulo 2^16 + 1 przez Z5 
8.  Wyniki  operacji 6 i 7 dodajemy modulo 2^16
9.  Wynik  operacji 8 mnożymy modulo 2^16 + 1 przez Z6 
10.  Wyniki  operacji 7 i 9 sumujemy modulo 2^16
11.  Wyniki  operacji 1 i 9 poddajemy  operacji XOR-owania
12.  Wyniki  operacji 3 i 9 poddajemy  operacji XOR-owania
13.  Wyniki  operacji 2 i 10 poddajemy  operacji XOR-owania
14.  Wyniki  operacji 4 i 10 poddajemy  operacji XOR-owania
    
Dodatkowo  na  samym  końcu  należy  zamienić  miejscami  dwa  wewnętrzne  bloki (X2 z X3). Otrzymane  cztery  bloki (X1-X4) wrzucamy do następnej ‘rundy’ aż  operacje  zostaną  wykonane  osiem  razy. Po zakończeniu  wywołujemy  jeszcze  transformację  końcową.

## Transformacja  wyjściowa/końcowa  wygląda w następujący  sposób:

Z1-Z4 oznaczają  podklucze, natomiast X1-X4 oznaczają  podbloki 64-bitowego bloku  wejściowego  poddanego  wcześniejszym 8 ‘rundom’

1.  X1 mnożymy modulo 2^16 + 1 przez Z1
2.  Dodajemy X2 modulo 2^16 do Z2 
3.  Dodajemy X3 modulo 2^16 do Z3
4.  X4 mnożymy modulo 2_16 + 1 przez Z4
