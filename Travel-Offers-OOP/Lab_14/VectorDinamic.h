//
// Created by Blondi on 4/10/2025.
//

#ifndef VECTORDINAMIC_H
#define VECTORDINAMIC_H
#include <stdexcept>
const int INITIAL_CAPACITY=5;


template<typename ElementT>
class IteratorVectorT;

template<typename ElementT>
class VectorDinamicT {
public:
    ///Constructor default
    VectorDinamicT();

    ///Constructor de copiere
    VectorDinamicT(const VectorDinamicT& ot);

    ///Eliberare memorie
    ~VectorDinamicT();

    ///Operator assignment
    VectorDinamicT& operator=(const VectorDinamicT& ot);

    ///Move constructor
    VectorDinamicT(VectorDinamicT&& ot);

    ///Move assignment
    VectorDinamicT& operator=(VectorDinamicT&& ot);

    void add(const ElementT& el);

    ElementT& get(int poz) const;

    void remove(int poz);

    void set(int poz, const ElementT& el);

    int size() const noexcept;

    friend class IteratorVectorT<ElementT>;
    //functii care creaza iteratori
    IteratorVectorT<ElementT> begin();
    IteratorVectorT<ElementT> end();

private:
    int lg;
    int cap;
    ElementT* elems;

    void ensureCapacity();
};

///Constructor default
template<typename ElementT>
VectorDinamicT<ElementT>::VectorDinamicT() : elems{ new ElementT [INITIAL_CAPACITY] }, cap { INITIAL_CAPACITY }, lg { 0 } {
}

///Constructor de copiere
template<typename ElementT>
VectorDinamicT<ElementT>::VectorDinamicT(const VectorDinamicT<ElementT>& ot) {
    elems = new ElementT[ot.cap];
    for (int i = 0; i < ot.lg; i++) {
        elems[i] = ot.elems[i];
    }
    lg = ot.lg;
    cap = ot.cap;
}

///Operator assignment
template<typename ElementT>
VectorDinamicT<ElementT> &VectorDinamicT<ElementT>::operator=(const VectorDinamicT<ElementT>& ot) {
    if (this == &ot) {
        return *this;
    }
    delete[] elems;
    elems = new ElementT[ot.cap];
    ///copiez elemente
    for (int i = 0; i < ot.lg; i++) {
        elems[i] = ot.elems[i];
    }
    lg = ot.lg;
    cap = ot.cap;
    return *this;
}

///Eliberam memoria
template<typename ElementT>
VectorDinamicT<ElementT>::~VectorDinamicT() {
    delete[] elems;
}

///Move constructor
template<typename ElementT>
VectorDinamicT<ElementT>::VectorDinamicT(VectorDinamicT&& ot) {
    ///Copy the data pointer from other
    elems = ot.elems;
    lg = ot.lg;
    cap = ot.cap;
    // Release the data pointer from the source object so that
    // the destructor does not free the memory multiple times.
    ot.elems = nullptr;
    ot.lg = 0;
    ot.cap = 0;
}

///Move assignment
template<typename ElementT>
VectorDinamicT<ElementT> &VectorDinamicT<ElementT>::operator=(VectorDinamicT<ElementT>&& ot) {
    if (this == &ot) {
        return *this;
    }
    delete[] elems;
    elems = ot.elems;
    lg = ot.lg;
    cap = ot.cap;
    ot.elems = nullptr;
    ot.lg = 0;
    ot.cap = 0;
    return *this;
}

template<typename ElementT>
void VectorDinamicT<ElementT>::add(const ElementT& el) {
    ensureCapacity();
    elems[lg++] = el;
}

template<typename ElementT>
void VectorDinamicT<ElementT>::remove(int poz) {
    if (poz<0 || poz>=lg) {
        throw std::out_of_range("VectorDinamicT<ElementT>::remov...Pozitie invalida");
    }
    for (int i=poz;i<lg-1;i++) {
        elems[i]=elems[i+1];
    }
    lg--;
}

template<typename ElementT>
ElementT &VectorDinamicT<ElementT>::get(int poz) const {
    return elems[poz];
}

template<typename ElementT>
void VectorDinamicT<ElementT>::set(int poz, const ElementT& el) {
    elems[poz] = el;
}

template<typename ElementT>
int VectorDinamicT<ElementT>::size() const noexcept {
    return lg;
}

template<typename ElementT>
void VectorDinamicT<ElementT>::ensureCapacity() {
    if (lg < cap) {
        return;
    }
    cap *= 2;
    ElementT* newelems = new ElementT[cap];
    for (int i = 0; i < lg; i++) {
        newelems[i] = elems[i];
    }
    delete[] elems;
    elems = newelems;
}

template<typename ElementT>
IteratorVectorT<ElementT> VectorDinamicT<ElementT>::begin() {
    return IteratorVectorT<ElementT>(*this);
}

template<typename ElementT>
IteratorVectorT<ElementT> VectorDinamicT<ElementT>::end() {
    return IteratorVectorT<ElementT>(*this, lg);
}

template<typename ElementT>
class IteratorVectorT {
private:
    const VectorDinamicT<ElementT>& vec;
    int poz = 0;
public:
    IteratorVectorT(const VectorDinamicT<ElementT>& vec) noexcept;
    IteratorVectorT(const VectorDinamicT<ElementT>& vec, int poz) noexcept;
    bool valid() const;
    ElementT& element() const;
    void next();
    bool curent();
    ElementT& operator*();
    IteratorVectorT& operator++();
    bool operator==(const IteratorVectorT& other) noexcept;
    bool operator!=(const IteratorVectorT& other) noexcept;
};

template<typename ElementT>
IteratorVectorT<ElementT>::IteratorVectorT(const VectorDinamicT<ElementT>& vec) noexcept: vec{ vec}  {
}

template<typename ElementT>
IteratorVectorT<ElementT>::IteratorVectorT(const VectorDinamicT<ElementT>& vec, int poz) noexcept : vec{ vec }, poz{ poz } {
}

template<typename ElementT>
bool IteratorVectorT<ElementT>::valid()const {
    return poz < vec.lg;
}

template<typename ElementT>
ElementT &IteratorVectorT<ElementT>::element() const {
    return vec.elems[poz];
}

template<typename ElementT>
void IteratorVectorT<ElementT>::next() {
    poz++;
}

template<typename ElementT>
bool IteratorVectorT<ElementT>::curent() {
    return poz;
}

template<typename ElementT>
ElementT &IteratorVectorT<ElementT>::operator*() {
    return element();
}

template<typename ElementT>
IteratorVectorT<ElementT> &IteratorVectorT<ElementT>::operator++() {
    next();
    return *this;
}

template<typename ElementT>
bool IteratorVectorT<ElementT>::operator==(const IteratorVectorT<ElementT>& other) noexcept {
    return poz == other.poz;
}

template<typename ElementT>
bool IteratorVectorT<ElementT>::operator!=(const IteratorVectorT<ElementT>& other) noexcept {
    return !(*this == other);
}

#endif //VECTORDINAMIC_H
