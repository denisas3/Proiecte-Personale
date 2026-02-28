//
// Created by Blondi on 3/31/2025.
//

#include "Validator_oferta.h"
#include <string>
#include <cmath>
#include "../Erori/Exceptii.h"
using std::string;

void Validator_oferta::validare_oferta(const int& id, const string& denumire, const string& destinatie, const string& tip, const double& pret) {
    if (id <= 0)
        throw Exceptii("Id invalid!\n");
    if (denumire == "")
        throw Exceptii("Denumire invalida!\n");
    if (destinatie == "")
        throw Exceptii("Destinatie invalida!\n");
    if (tip == "")
        throw Exceptii("Tip invalida!\n");
    if (pret <= 0)
        throw Exceptii("Pret invalida!\n");
}