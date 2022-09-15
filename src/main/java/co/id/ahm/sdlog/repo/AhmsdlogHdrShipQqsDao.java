package co.id.ahm.sdlog.repo;

import co.id.ahm.sdlog.model.AhmsdlogHdrShipQqs;
import co.id.ahm.sdlog.model.AhmsdlogHdrShipQqsPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AhmsdlogHdrShipQqsDao extends JpaRepository<AhmsdlogHdrShipQqs, AhmsdlogHdrShipQqsPk> {
}
