//
// Created by Blondi on 3/31/2025.
//

#ifndef CONSOLA_H
#define CONSOLA_H
#include "../Service/Service_oferta.h"
#include "../Teste/Teste.h"

class Consola {

    // Service_oferta service;
    // Teste teste;
    private:
        Service_oferta& service;
        Teste& teste;
    public:

        Consola(Service_oferta& service, Teste& teste);
        void runUi();
        void ruleaza_toate_testele();

};



#endif //CONSOLA_H
