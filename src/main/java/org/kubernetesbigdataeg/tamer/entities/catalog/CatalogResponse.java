package org.kubernetesbigdataeg.tamer.entities.catalog;

import java.util.List;

public class CatalogResponse {
    private List<Catalog> catalogs;

    public CatalogResponse(List<Catalog> catalogList) {
        setCatalogs(catalogList);
    }

    public void setCatalogs(List<Catalog> catalogList) {
        this.catalogs = catalogList;
    }

    // The name of the method: getTHENAME it's the
    // key of the JSON, the return te values.
    public List<Catalog> getCatalogs() {
        return catalogs;
    }
}
