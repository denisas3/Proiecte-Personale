//
// Created by Blondi on 5/29/2025.
//

#include "model.h"

int Model::rowCount(const QModelIndex&) const {
	return service.service_get_cos().size();
}

int Model::columnCount(const QModelIndex&) const {
	return 5;
}

QVariant Model::data(const QModelIndex &index, int role) const {
	if (!index.isValid()) return QVariant();

	int row = index.row();
	int column = index.column();

	if (role == Qt::DisplayRole) {
		const Oferta& oferta = service.service_get_cos().at(row);

		switch (column) {
			case 0:
				return oferta.getId();
			case 1:
				return QString::fromStdString(oferta.getDenumire());
			case 2:
				return QString::fromStdString(oferta.getDestinatie());
			case 4:
				return oferta.getPret();
			case 3:
				return QString::fromStdString(oferta.getTip());
			default:
				return QVariant();
		}
	}

	return QVariant();
}

QVariant Model::headerData(int section, Qt::Orientation orientation, int role) const {
	if (role != Qt::DisplayRole) return QVariant();

	if (orientation == Qt::Horizontal) {
		switch (section) {
			case 0: return QString("Id oferta");
			case 1: return QString("Denumire");
			case 2: return QString("Destinatie");
			case 3: return QString("Tip");
			case 4: return QString("Pret");
			default: return QVariant();
		}
	}

	return QVariant();
}

void Model::updateModel() {
	beginResetModel();
	endResetModel();
}
