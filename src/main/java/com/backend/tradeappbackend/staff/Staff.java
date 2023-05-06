package com.backend.tradeappbackend.staff;

import com.backend.tradeappbackend.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tbl_staffs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Staff extends User {

    // This uniquely identifies the staff and is able to be publicly shared without any worries of leaking database size and
    // scrappers being able to sequentially scrape these links
    private UUID affiliateCode = UUID.randomUUID();
}
