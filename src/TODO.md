### Mohammad
- [ ] getForm Operation clean machen.
- [ ] Add und remove coefficient methode.

### High prio
- [X] SoloConstraints doch einbauen (bei <= 0 -> Substituieren mit -SoloConstraint (ÜB 2))
- [ ] Befehl um das lineare Program final zu bestätigen, um NormalFormOperation, Simplex etc freizuschalten...

### Medium prio
- [ ] keine modification operations nach NormalFormOperation (Simplex etc. erst mit NormalForm möglich!
- [X] SoloConstraints integration in /show -> x1, x2... xn >= 0
- [X] Unterscheidung zwischen Slack und Normalen Variablen (auch bei show)

### Low prio
- [ ] right align Nichtnegativitätsbedingungen (/show)
- [ ] Add usage info in README.md and command-line for commands
- [ ] Make the AddConstraint command useable with one line: /addconstraint 1 2 3 <= 4 (habe es schon damals so gemacht, man könnte die Lösung aus einer früheren Version nehmen)
- [X] SoloConstraint in DecisionVariable umbennenen
- [X] x1, x2, ... xn Zeile bei /show


### Ideen
- SetColumn Befehl?



### Für die Zukunft
- grafische Lösung


### Wichtige Begriffe
- Koeffizientematrix
- Zulässiger Punkt
- Optimaler Punkt
- Ecken (Extrempunkte von konvexen Mengen)