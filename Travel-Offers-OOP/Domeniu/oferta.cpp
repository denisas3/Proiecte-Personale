//
// Created by Blondi on 3/29/2025.
//

#include "oferta.h"
#include <iostream>
using std::cout;

Oferta::Oferta(const int& id, const string& denumire, const string& destinatie, const string& tip, const double& pret): id{id}, denumire{denumire}, destinatie{destinatie}, tip{tip}, pret{pret} {

}

int Oferta::getId() const {
    return id;
}

string Oferta::getDenumire() const{
    return denumire;
}

string Oferta::getDestinatie() const{
    return destinatie;
}

string Oferta::getTip() const{
    return tip;
}

double Oferta::getPret() const{
    return pret;
}

void Oferta::setDenumire(const string& denumire_noua) {
    denumire = denumire_noua;
}

void Oferta::setDestinatie(const string& destinatie_noua) {
    destinatie = destinatie_noua;
}

void Oferta::setTip(const string& tip_nou) {
    tip = tip_nou;
}

void Oferta::setPret(const double& pret_nou) {
    pret = pret_nou;
}

Oferta::Oferta(const Oferta& other):id{other.id}, denumire{other.denumire}, destinatie{other.destinatie}, tip{other.tip}, pret{other.pret} {
}

