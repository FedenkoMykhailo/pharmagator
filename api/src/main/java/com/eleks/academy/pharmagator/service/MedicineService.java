package com.eleks.academy.pharmagator.service;

import com.eleks.academy.pharmagator.dto.MedicinesDto;
import com.eleks.academy.pharmagator.entities.Medicine;

import java.util.List;
import java.util.Optional;

public interface MedicineService {

    List<MedicinesDto> findAllMedicine();

    MedicinesDto findMedicineById(Long id);

    MedicinesDto saveMedicine(MedicinesDto dto);

    MedicinesDto updateMedicine(MedicinesDto dto, Long id);

    void deleteMedicine(Long id);

    Optional<Medicine> findMedicineByTitle(String title);
}
