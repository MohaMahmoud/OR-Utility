### Mohammad
- [ ] getForm Operation clean machen.
- [ ] Add und remove coefficient methode.

### High prio
- [X] Constraints to = function (mit Schlupfvariablen. Zur Abgrenzung die anderen Strukturvariablen nennen)
- [X] ChangeObjectiveFunctionOperation
- [X] split method of = constraints
- [X] Finish standard from command
- [X] Constraint negation
- [X] getForm()
- [ ] SoloConstraints doch einbauen (bei <= 0 -> Substituieren mit -SoloConstraint (ÜB 2))

### Medium prio
- [ ] Add usage info in README.md and command-line for commands
- [X] SoloConstraints integration in /show -> x1, x2... xn >= 0
- [ ] Make the AddConstraint command useable with one line: /addconstraint 1 2 3 <= 4 (habe es schon damals so gemacht, man könnte die Lösung aus einer früheren Version nehmen)

### Low prio
- [X] x1, x2, ... xn Zeile bei /show
- [ ] right align Nichtnegativitätsbedingungen (/show)
- [X] SoloConstraint in DecisionVariable umbennenen


### Ideen
- grafische Lösung (Zukunft)
- SetColumn Befehl?


### Wichtige Begriffe
- Koeffizientematrix
- Zulässiger Punkt
- Optimaler Punkt
- Ecken (Extrempunkte von konvexen Mengen)