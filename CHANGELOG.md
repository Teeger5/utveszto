> 2025 03. 26. SZ
- Az alap program elkészült
  - Egy bemeneti int mátrixban meg tudja keresni az útvonalat 
  a legkisebb számtól a legnagyobb hoz
  - Minden mezőt érint
  - A számokat tartalmazókat csak növekvő sorrendben
  - A legutolső mező a legnagyobb számot tartalmazó mező
- Az útvonalat listaszerűen írja ki, azaz a koordinátákit a mezőknek sorban egymás alá
- Megkeresi előre a táblában a legnagyobb számot, hogy gyorsabban tudjon dönteni arról,
vissza kell-e lépnie egyet (elérte a legnagyobb számot, de még nem járt minden mezőn)
  - Meg kell kérdezni, hogy ez szabályos-e

> 2025 03. 27. CS
- Mostmár nem veszi figyelembe a legnagyobb számot tartalmazó mezőt az útkeresés,
hanem akkor áll meg, ha bejárta az egész táblát, és ellenőrzi, számot tartalmazó mezőn áll-e
  - Azaz nem üres mezőn
  - Mivel garantált, hogy sorban érinti a számokat, ezért ha a legutolső mező, amin áll,
  számot tartalmaz, akkor az a legnagyobb szám, ezért megtalálta a keresett útvonalat
  - Ezzel több lépést tesz viszont, mivel mindenképp végignézi az egész táblát,
  mégha elhagyta is a legnagyobb számot, és csak üres mezők vannak már meglátogatatlanul