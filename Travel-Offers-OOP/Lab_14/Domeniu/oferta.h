//
// Created by Blondi on 3/29/2025.
//

#ifndef OFERTA_H
#define OFERTA_H
#include <string>
using std::string;


class Oferta {
    private:
        int id;
        string denumire;
        string destinatie;
        string tip;
        double pret;

    public:
        int getId() const;
        string getDenumire() const;
        string getDestinatie() const;
        string getTip() const;
        double getPret() const;
        Oferta()=default;
        Oferta(const int& id, const string& denumire, const string& destinatie, const string& tip, const double& pret);
        Oferta(const Oferta& other);
        Oferta &operator=(const Oferta& other) {
            this->id = other.id;
            this->denumire = other.denumire;
            this->destinatie = other.destinatie;
            this->tip = other.tip;
            this->pret = other.pret;
            return *this;
        }
        void setDenumire(const string& denumire_noua);
        void setDestinatie(const string& destinatie_noua);
        void setTip(const string& tip_noua);
        void setPret(const double& pret_noua);
};


#endif //OFERTA_H
