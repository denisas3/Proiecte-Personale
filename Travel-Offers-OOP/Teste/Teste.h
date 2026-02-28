//
// Created by Blondi on 4/1/2025.
//

#ifndef TESTE_H
#define TESTE_H
#include "../Repo/Repo_oferta.h"
#include "../Service/Service_oferta.h"


class Teste {
    private:
        Repo_oferta repo;
        Service_oferta& service;
        Cos cos;
    public:
        Teste(const Repo_oferta& repo, Service_oferta& service, const Cos& cos);
        static void test_get();
        static void test_set();
        static void test_validare();
        void test_repo_adauga();
        void test_repo_sterge();
        void test_repo_modifica();
        void test_repo_cautare_denumire();
        void test_repo_cautare_destinatie();
        void test_repo_cautare_tip();
        void test_repo_cautare_pret();
        void test_repo_filtrare();
        void test_repo_sortare_denumire();
        void test_repo_sortare_destinatie();
        void test_repo_sortare_tip();
        void test_service_adauga();
        void test_service_modifica();
        void test_service_cautare_denumire();
        void test_service_cautare_destinatie();
        void test_service_cautare_tip();
        void test_service_cautare_pret();
        void test_service_filtrare();
        void test_service_sortare_denumire();
        void test_service_sortare_destinatie();
        void test_service_sortare_tip_pret();
        void test_adauga_in_cos();
        void test_goleste_cos();
        void test_get_cos();
        void test_size_cos();
        void teste_service_adauga_in_cos();
        void teste_service_size_cos();
        void test_service_goleste_cos();
        void test_service_get_cos();
        void test_exportCos();
        void test_servicev_export_cos();
        void test_service_undo_adauga();
        void test_service_undo_sterge();
        void test_service_dictionar_tip();
        void test_service_undo_modifica();
};



#endif //TESTE_H
