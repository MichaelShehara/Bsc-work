import { Component, OnDestroy, OnInit } from '@angular/core';
import { NbToastrService } from '@nebular/theme';
import { NbThemeService } from '@nebular/theme';
import { ChartDataSets, ChartOptions, ChartType } from 'chart.js';
import * as moment from 'moment';
import { Color, Label } from 'ng2-charts';
import { ReportsService } from 'src/app/service/reports.service';
import { SprintService } from 'src/app/service/sprint.service';
import { StateService } from 'src/app/service/state.service';

@Component({
  selector: 'app-defect-trend',
  templateUrl: './defect-trend.component.html',
  styleUrls: ['./defect-trend.component.css']
})

export class DefectTrendComponent implements OnInit {

  defectChartData: any = [];
  projectID: any;
  barChartData: ChartDataSets[] = [
    {
      data: [],
      label: 'Release vs. Issue'
    },
  ];
  barChartLabels: Label[] = [];
  barChartOptions: ChartOptions = {
    responsive: true,
    scales: {
      yAxes: [{
        ticks: {
          beginAtZero: true
        }
      }]
    }
  };
  barChartLegend = true;
  barChartType: ChartType = 'bar';
  barChartPlugins = [];
  barChartColors: Color[] = [
    {
      backgroundColor: 'transparent',
      borderColor: '#00d68f',
    },
    {
      backgroundColor: 'transparent',
      borderColor: '#BC2550',
    },
  ];
  themeSubcription: any;
  constructor(
    private reportsService: ReportsService,
    private sprintService: SprintService,
    private stateService: StateService,
    private toastrService: NbToastrService,
    private theme: NbThemeService
  ) {}


  ngOnInit() {

    this.stateService.selectedProjectId.subscribe((data) => {
      this.projectID = Number(data);
      if (this.projectID !== 0) {
        this.reportsService.getDetectsChartBySprintID(this.projectID).subscribe((data) => {
          if (data) {
            this.defectChartData.push(data);
            console.log(this.defectChartData);
            if (this.defectChartData.length !== 0) {
              this.defectChartData[0].forEach((element) => {
                if (element) {
                  this.barChartData[0].data.push(element.issueCount);
                  this.barChartLabels.push(element.releaseName);
                }
              });
            }
          }
        });
      } else {
      }
    });
  }

  chartClicked({ event, active }: { event: MouseEvent, active: {}[] }): void {
  }

}
