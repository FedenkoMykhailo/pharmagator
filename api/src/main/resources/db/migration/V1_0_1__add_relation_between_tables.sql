
ALTER TABLE prices
    ADD CONSTRAINT fk_pharmacy
    FOREIGN KEY (pharmacy_id)
    REFERENCES pharmacies(id);

ALTER TABLE prices
    ADD CONSTRAINT fk_medicine
    FOREIGN KEY (medicine_id)
    REFERENCES medicines(id);