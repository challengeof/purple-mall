package net.caidingke.rabbitmq.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author bowen
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RabbitMessage implements Serializable {

    private Long id;
}
