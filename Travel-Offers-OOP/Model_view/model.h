//
// Created by Blondi on 5/29/2025.
//

#ifndef MODEL_H
#define MODEL_H
#include <QAbstractTableModel>
#include "../Service/Service_oferta.h"

class Model : public QAbstractTableModel {
	Q_OBJECT
private:
	Service_oferta &service;
public:
	Model(Service_oferta &service): service(service) {}

	int rowCount(const QModelIndex &parent = QModelIndex()) const override;

	int columnCount(const QModelIndex &parent = QModelIndex()) const override;

	QVariant headerData(int section, Qt::Orientation orientation, int role = Qt::DisplayRole) const override;

	QVariant data(const QModelIndex &index, int role) const override;

	void updateModel();

};

#endif //MODEL_H
