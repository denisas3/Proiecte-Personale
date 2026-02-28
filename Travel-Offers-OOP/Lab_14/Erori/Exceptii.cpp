//
// Created by Blondi on 3/31/2025.
//

#include "Exceptii.h"

Exceptii::Exceptii(const string& mesaj) : mesaj(mesaj) {

}

const char* Exceptii::what() const noexcept{
    return mesaj.c_str();
}