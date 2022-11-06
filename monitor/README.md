## Usage
```bash
$ docker compose up -d
```

## Prometheus
- version: v2.39.1
- `prometheus-ip:9090`
- scrape metrics per 15 seconds

## Grafana
- grafana: v9.2.3
- `grafana-ip:3000`
- Use provisioning (datasource, dashboard)
- default dashboard
  - [Spring boot system monitor](https://grafana.com/grafana/dashboards/11378-justai-system-monitor/)
  - [JVM (Micrometer)](https://grafana.com/grafana/dashboards/4701-jvm-micrometer/)
