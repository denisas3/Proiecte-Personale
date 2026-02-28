//
// Created by Blondi on 4/29/2025.
//

#include "cos.h"
#include <random>
#include <algorithm>
#include <chrono>
#include <math.h>
#include <valarray>
#include <fstream>
#include <iostream>
using namespace std;

void Cos::goleste_cos() {
    return cos.clear();
}

void Cos::adauga_in_cos(const Oferta &oferta) {
    cos.push_back(oferta);
}

vector<Oferta> Cos::genereaza_cos(int nr_oferte, vector<Oferta> &oferte) {
    cos.clear();

    std::mt19937 mt{std::random_device{}()};
    std::uniform_int_distribution<> dist{0,nr_oferte};
    int random_nr = dist(mt);  //nr aleator intre 0 si nr total de oferte
    auto seed = std::chrono::system_clock::now().time_since_epoch().count();
    std::shuffle(oferte.begin(), oferte.end(), std::default_random_engine(seed)); //amesteca vectorul in care se afla ofertele

    for (int i = 0; i < random_nr; i++) {
        cos.push_back(oferte[i]);
    }

    return cos;
}


const vector<Oferta> &Cos::get_cos() const {
    return cos;
}

int Cos::size_cos() const {
    return cos.size();
}

void Cos::exportCos(const std::string &numeFisier) const {
    std::ofstream fisier(numeFisier);
    for (const auto &oferta : this->get_cos()) {
        fisier << oferta.getId() << "," << oferta.getDenumire() << "," << oferta.getDestinatie() << "," << oferta.getTip() << "," << oferta.getPret() << "\n";
    }
    fisier.close();
}

