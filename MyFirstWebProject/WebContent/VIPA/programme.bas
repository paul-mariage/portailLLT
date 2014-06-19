Rem --- eWON start section: Cyclic Section
ewon_cyclic_section:
Rem --- eWON user (start)
Rem --- eWON user (end)
End
Rem --- eWON end section: Cyclic Section
Rem --- eWON start section: Init Section
ewon_init_section:
Rem --- eWON user (start)
PUTHTTP "192.168.0.151:8080","/PortailLLT/Upload","Message=PremierEnvoi","","failed"

REM ----------------------------------------------------------

OPEN "sauvegarde" FOR TEXT OUTPUT AS 1
PUT 1,"Nouvelles valeurs"
CLOSE 1


REM -----------------Definition des evenements----------------
REM Timer pour l'enregistrement des variables du système
TSET 1,5
TSET 2,60
TSET 3,63

REM events

ONTIMER 1,"GOTO SaveValues"
ONTIMER 2,"GOTO SendFileHTTP"
ONTIMER 3,"GOTO RazFile"
REM ----------------------------------------------------------

REM ------------------------Fonctions-------------------------
SendFileHTTP:
PRINT "Envoi du fichier"
REM Cette partie va envoyer le fichier contenant les valeurs toutes les 5 minutes
REM Recuperation de la date et de l'heure
t$=TIME$
d$=t$(1 TO 10)
h$=t$(12 TO 16)
s$=t$(18 TO 19)
t$=" le "+d$+" à "+h$+" et "+s$+"sec"
PUTHTTP "192.168.0.151:8080","/PortailLLT/UploadFile","Message=SendHTTP","test[]=[$dtUF $ftT $uf/sauvegarde]","failed"
End

SaveValues:
REM Cette partie va enregistre dans le fichier de valeur l'ensemble des variables toutes les 5 secondes
PRINT "Nouvelle Sauvegarde de variable"

t$=TIME$
d$=t$(1 TO 10)
h$=t$(12 TO 13)
m$=t$(15 TO 16)
s$=t$(18 TO 19)
t$=d$+"/"+h$+"/"+m$+"/"+s$
REM Nom du site
z$="SiteTest"

REM Ecriture des variables

OPEN "sauvegarde" FOR TEXT APPEND AS 1
PUT 1,z$;t$;"var1";GETIO "var1"
PUT 1,z$;t$;"var2";GETIO "var2"
PUT 1,z$;t$;"var3";GETIO "var3"

CLOSE 1

LOGEVENT "Enregistrement fichier production",199
PUTHTTP "192.168.0.151:8080","/PortailLLT/Upload","Message=Sauvegarde des variable"+t$,"","failed"
TSET 1,5
End

RazFile:
PRINT "RAZ FILE"
OPEN "sauvegarde" FOR TEXT OUTPUT AS 1
PUT 1,"Nouvelles valeurs"
CLOSE 1

End


Rem --- eWON user (end)
End
Rem --- eWON end section: Init Section
