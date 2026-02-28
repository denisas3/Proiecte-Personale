//
// Created by Blondi on 3/31/2025.
//

#ifndef SERVICE_OFERTA_H
#define SERVICE_OFERTA_H
#include <memory>
#include <string>

#include "../GUI/Observer.h"
#include "../Wishlist/cos.h"
#include "../Undo/Undo.h"
using std::string;
#include "../Repo/Repo_oferta.h"
#include "../Validator/Validator_oferta.h"
#include <vector>
#include <map>
using std::vector;


class Service_oferta: public Observable {
    private:
        Repo_oferta& repo;
        Validator_oferta validator;
        Cos cos;
        vector<std::unique_ptr<Undo>> undo;


    public:
        Service_oferta(Repo_oferta& repo, const Validator_oferta& validator, const Cos& cos);
        ~Service_oferta() = default;
        //Service_oferta(const Service_oferta&) = delete;
        //Service_oferta& operator=(const Service_oferta&) = delete;
        void service_adauga(const int& id, const string& denumire, const string& destinatie, const string& tip, const double& pret);
        void service_sterge(const int& id, const string& denumire, const string& destinatie, const string& tip, const double& pret);
        void service_modifica(const int& id, const string& denumire_noua, const string& destinatie_noua, const string& tip_nou, const double& pret_nou);
        vector<Oferta> service_cautare_denumire(const string& denumire);
        vector<Oferta> service_cautare_destinatie(const string& destinatie);
        vector<Oferta> service_cautare_tip(const string& tip);
        vector<Oferta> service_cautare_pret(const double& pret);
        vector<Oferta> service_filtrare(const string& destinatie, const double& pret);
        vector<Oferta> service_sortare_denumire(int ok);
        vector<Oferta> service_sortare_destinatie(int ok);
        vector<Oferta> service_sortare_tip_pret(int ok);
        vector<Oferta> get_oferte();
        vector<Oferta> service_adauga_in_cos(int id);
        vector<Oferta> service_cautare_denumire_id(const string& denumire, const int& id);
        int service_size_cos();
        void service_goleste_cos();
        vector<Oferta> service_genereaza_cos();
        vector<Oferta> service_get_cos();
        void service_exportCvs(const string& numeFisier) const;
        std::map<std::string , std::vector<Oferta>> service_dictionar_tip();
        void service_undo();
};


#endif //SERVICE_OFERTA_H
