# PAO

## Acțiuni/Interogări:


1. Vizualizarea tuturor imaginilor/videoclipurilor din galerie
2. Adaugare imagine/videoclip in galerie
3. Stergere imagine/videoclip din galerie
4. Cautare imagine/videoclip in galerie dupa nume
5. Actualizare (nume si descriere) imagine/videoclip din galerie 
6. Adaugare/Stergere eticheta la imagine/videoclip
7. Vizualizare elemente dupa eticheta
8. Sortare imagini/videoclipuri dupa diferite criterii
9. Creare album gol
10. Adaugare elemente in album
11. Stergere element din album
12. Stergere album
13. Filtrare imagini/videoclipuri dupa diferite criterii
## Tipuri de obiecte:


1.Element 
- o entitate care reprezinta un videoclip sau o imagine în general. 
Aceasta  are mai multe atribute, cum ar fi nume, descriere, dimensiune, dată și oră a creării.

2.Videoclip
- o entitate derivata din element care include informatii suplimentare precum
durata filmuletului

3.Imagine: 
- o entitate derivata din element care include rezoluția sau locația.

4.Fotografie: 
- aceasta este o entitate derivată din imagine, care include informații suplimentare, cum ar fi tipul aparatului de fotografiat și setările folosite în momentul realizării fotografiei.

5.Etichetă: 
- o etichetă este o entitate care reprezintă o categorie sau o temă asociată cu o imagine sau cu mai multe imagini. Aceasta poate fi utilizată pentru a organiza și gestiona imaginile în funcție de diverse criterii, cum ar fi subiectul, locația, anul sau persoanele prezente.

6.Album: 
- un album este o entitate care grupează mai multe imagini sau videoclipuri în funcție de anumite criterii. Acesta poate fi utilizat pentru a organiza și gestiona imaginile si videoclipurile în funcție de diverse criterii, cum ar fi evenimente, locații sau teme.

7.Serviciu de galerie: 
- o clasă de servicii care oferă operațiile sistemului, cum ar fi adăugarea, ștergerea, căutarea și sortarea imaginilor, crearea și gestionarea etichetelor și a albumelor, precum și alte operații specifice.

8.Filtru: 
- o clasă utilizată pentru a filtra imaginile în funcție de diverse criterii, cum ar fi numele, data, dimensiunea sau alte atribute.

