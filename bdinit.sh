#!/usr/bin/sh

# Inicializar la base de datos del juego.
java -classpath ".:sqlite-jdbc-3.30.1.jar" BDInit
